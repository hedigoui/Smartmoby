<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity]
#[ORM\Table(name: "trajet")]
class Trajet
{
    #[ORM\Id]
    #[ORM\GeneratedValue(strategy: "IDENTITY")]
    #[ORM\Column(name: "id", type: "integer")]
    private ?int $id = null;

    #[ORM\ManyToOne(targetEntity: Vehicule::class, inversedBy: "trajets")]
    #[ORM\JoinColumn(name: "id_veh", referencedColumnName: "id", nullable: false)]
    #[Assert\NotNull(message: "Le véhicule est obligatoire")]
    private ?Vehicule $vehicule = null;

    #[ORM\Column(name: "pointD", type: "string", length: 100)]
    #[Assert\NotBlank(message: "Le point de départ est obligatoire")]
    #[Assert\Length(
        min: 2,
        max: 100,
        minMessage: "Le point de départ doit faire au moins {{ limit }} caractères",
        maxMessage: "Le point de départ ne peut pas dépasser {{ limit }} caractères"
    )]
    private ?string $pointDepart = null;

    #[ORM\Column(name: "pointA", type: "string", length: 100)]
    #[Assert\NotBlank(message: "Le point d'arrivée est obligatoire")]
    #[Assert\Length(
        min: 2,
        max: 100,
        minMessage: "Le point d'arrivée doit faire au moins {{ limit }} caractères",
        maxMessage: "Le point d'arrivée ne peut pas dépasser {{ limit }} caractères"
    )]
    private ?string $pointArrivee = null;

    #[ORM\Column(name: "dateD", type: "datetime")]
    #[Assert\NotBlank(message: "La date de départ est obligatoire")]
    #[Assert\GreaterThan("today", message: "La date de départ doit être dans le futur")]
    private ?\DateTimeInterface $dateDepart = null;

    #[ORM\Column(name: "dateA", type: "datetime")]
    #[Assert\NotBlank(message: "La date d'arrivée est obligatoire")]
    #[Assert\Expression(
        "this.getDateArrivee() > this.getDateDepart()",
        message: "La date d'arrivée doit être postérieure à la date de départ"
    )]
    private ?\DateTimeInterface $dateArrivee = null;

    #[ORM\Column(name: "distance", type: "float")]
    #[Assert\NotBlank(message: "La distance est obligatoire")]
    #[Assert\Positive(message: "La distance doit être positive")]
    private ?float $distance = null;

    #[ORM\Column(name: "prix", type: "float")]
    #[Assert\NotBlank(message: "Le prix est obligatoire")]
    #[Assert\Positive(message: "Le prix doit être positif")]
    private ?float $prix = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getVehicule(): ?Vehicule
    {
        return $this->vehicule;
    }

    public function setVehicule(?Vehicule $vehicule): self
    {
        $this->vehicule = $vehicule;
        return $this;
    }

    public function getPointDepart(): ?string
    {
        return $this->pointDepart;
    }

    public function setPointDepart(string $pointDepart): self
    {
        $this->pointDepart = $pointDepart;
        return $this;
    }

    public function getPointArrivee(): ?string
    {
        return $this->pointArrivee;
    }

    public function setPointArrivee(string $pointArrivee): self
    {
        $this->pointArrivee = $pointArrivee;
        return $this;
    }

    public function getDateDepart(): ?\DateTimeInterface
    {
        return $this->dateDepart;
    }

    public function setDateDepart(\DateTimeInterface $dateDepart): self
    {
        $this->dateDepart = $dateDepart;
        return $this;
    }

    public function getDateArrivee(): ?\DateTimeInterface
    {
        return $this->dateArrivee;
    }

    public function setDateArrivee(\DateTimeInterface $dateArrivee): self
    {
        $this->dateArrivee = $dateArrivee;
        return $this;
    }

    public function getDistance(): ?float
    {
        return $this->distance;
    }

    public function setDistance(float $distance): self
    {
        $this->distance = $distance;
        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;
        return $this;
    }
}
