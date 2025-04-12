<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;

#[ORM\Entity]
#[ORM\Table(name: "vehicule")]
class Vehicule
{
    #[ORM\Id]
    #[ORM\GeneratedValue(strategy: "AUTO")]
    #[ORM\Column(name: "id", type: "integer")]
    private ?int $id = null;

    #[ORM\Column(name: "type", type: "string", length: 50)]
    #[Assert\NotBlank(message: "Le type est obligatoire")]
    #[Assert\Length(
        min: 2,
        max: 50,
        minMessage: "Le type doit faire au moins {{ limit }} caractères",
        maxMessage: "Le type ne peut pas dépasser {{ limit }} caractères"
    )]
    #[Assert\Regex(
        pattern: "/^[a-zA-Z0-9\s\-]+$/",
        message: "Le type ne peut contenir que des lettres, des chiffres, des espaces et des tirets"
    )]
    private ?string $type = null;

    #[ORM\Column(name: "capacite", type: "integer")]
    #[Assert\NotBlank(message: "La capacité est obligatoire")]
    #[Assert\Type(type: "integer", message: "La capacité doit être un nombre entier")]
    #[Assert\Positive(message: "La capacité doit être positive")]
    #[Assert\LessThanOrEqual(value: 500, message: "La capacité ne peut pas dépasser {{ compared_value }}")]
    private ?int $capacite = null;

    #[ORM\Column(name: "statut", type: "string", length: 50)]
    #[Assert\NotBlank(message: "Le statut est obligatoire")]
    #[Assert\Choice(
        choices: ["available", "in use", "under maintenance"],
        message: "Le statut doit être 'available', 'in use' ou 'under maintenance'"
    )]
    private ?string $statut = null;

    #[ORM\Column(name: "dispo", type: "boolean")]
    #[Assert\NotNull(message: "La disponibilité est obligatoire")]
    private ?bool $dispo = null;

    #[ORM\OneToMany(mappedBy: "vehicule", targetEntity: Trajet::class)]
    private Collection $trajets;

    public function __construct()
    {
        $this->trajets = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): self
    {
        $this->type = $type;
        return $this;
    }

    public function getCapacite(): ?int
    {
        return $this->capacite;
    }

    public function setCapacite(int $capacite): self
    {
        $this->capacite = $capacite;
        return $this;
    }

    public function getStatut(): ?string
    {
        return $this->statut;
    }

    public function setStatut(string $statut): self
    {
        $this->statut = $statut;
        return $this;
    }

    public function isDispo(): ?bool
    {
        return $this->dispo;
    }

    public function setDispo(bool $dispo): self
    {
        $this->dispo = $dispo;
        return $this;
    }

    /**
     * @return Collection<int, Trajet>
     */
    public function getTrajets(): Collection
    {
        return $this->trajets;
    }

    public function addTrajet(Trajet $trajet): self
    {
        if (!$this->trajets->contains($trajet)) {
            $this->trajets->add($trajet);
            $trajet->setVehicule($this);
        }
        return $this;
    }

    public function removeTrajet(Trajet $trajet): self
    {
        if ($this->trajets->removeElement($trajet)) {
            // set the owning side to null (unless already changed)
            if ($trajet->getVehicule() === $this) {
                $trajet->setVehicule(null);
            }
        }
        return $this;
    }
}
