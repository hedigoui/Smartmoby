<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

use Doctrine\Common\Collections\Collection;
use App\Entity\Fedback;

#[ORM\Entity]
class Evenment
{

    #[ORM\Id]
    #[ORM\Column(type: "integer")]
    private int $id_event;

    #[ORM\Column(type: "string", length: 50)]
    private string $nom;

    #[ORM\Column(type: "date")]
    private \DateTimeInterface $date;

    #[ORM\Column(type: "string", length: 50)]
    private string $lieu;

    public function getId_event()
    {
        return $this->id_event;
    }

    public function setId_event($value)
    {
        $this->id_event = $value;
    }

    public function getNom()
    {
        return $this->nom;
    }

    public function setNom($value)
    {
        $this->nom = $value;
    }

    public function getDate()
    {
        return $this->date;
    }

    public function setDate($value)
    {
        $this->date = $value;
    }

    public function getLieu()
    {
        return $this->lieu;
    }

    public function setLieu($value)
    {
        $this->lieu = $value;
    }

    #[ORM\OneToMany(mappedBy: "id_event", targetEntity: Fedback::class)]
    private Collection $fedbacks;

        public function getFedbacks(): Collection
        {
            return $this->fedbacks;
        }
    
        public function addFedback(Fedback $fedback): self
        {
            if (!$this->fedbacks->contains($fedback)) {
                $this->fedbacks[] = $fedback;
                $fedback->setId_event($this);
            }
    
            return $this;
        }
    
        public function removeFedback(Fedback $fedback): self
        {
            if ($this->fedbacks->removeElement($fedback)) {
                // set the owning side to null (unless already changed)
                if ($fedback->getId_event() === $this) {
                    $fedback->setId_event(null);
                }
            }
    
            return $this;
        }
}
