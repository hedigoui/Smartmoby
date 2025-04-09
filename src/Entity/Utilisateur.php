<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

use Doctrine\Common\Collections\Collection;
use App\Entity\Organisateur;

#[ORM\Entity]
class Utilisateur
{

    #[ORM\Id]
    #[ORM\Column(type: "integer")]
    private int $id;

    #[ORM\Column(type: "string", length: 200)]
    private string $nom;

    #[ORM\Column(type: "string", length: 200)]
    private string $prenom;

    #[ORM\Column(type: "string", length: 200)]
    private string $nom_utilisateur;

    #[ORM\Column(type: "string", length: 200)]
    private string $email;

    #[ORM\Column(type: "string", length: 200)]
    private string $mot_de_passe;

    #[ORM\Column(type: "string")]
    private string $role;

    #[ORM\Column(type: "string", length: 6)]
    private string $reset_code;

    public function getId()
    {
        return $this->id;
    }

    public function setId($value)
    {
        $this->id = $value;
    }

    public function getNom()
    {
        return $this->nom;
    }

    public function setNom($value)
    {
        $this->nom = $value;
    }

    public function getPrenom()
    {
        return $this->prenom;
    }

    public function setPrenom($value)
    {
        $this->prenom = $value;
    }

    public function getNom_utilisateur()
    {
        return $this->nom_utilisateur;
    }

    public function setNom_utilisateur($value)
    {
        $this->nom_utilisateur = $value;
    }

    public function getEmail()
    {
        return $this->email;
    }

    public function setEmail($value)
    {
        $this->email = $value;
    }

    public function getMot_de_passe()
    {
        return $this->mot_de_passe;
    }

    public function setMot_de_passe($value)
    {
        $this->mot_de_passe = $value;
    }

    public function getRole()
    {
        return $this->role;
    }

    public function setRole($value)
    {
        $this->role = $value;
    }

    public function getReset_code()
    {
        return $this->reset_code;
    }

    public function setReset_code($value)
    {
        $this->reset_code = $value;
    }

    #[ORM\OneToMany(mappedBy: "id", targetEntity: Admin::class)]
    private Collection $admins;

        public function getAdmins(): Collection
        {
            return $this->admins;
        }
    
        public function addAdmin(Admin $admin): self
        {
            if (!$this->admins->contains($admin)) {
                $this->admins[] = $admin;
                $admin->setId($this);
            }
    
            return $this;
        }
    
        public function removeAdmin(Admin $admin): self
        {
            if ($this->admins->removeElement($admin)) {
                // set the owning side to null (unless already changed)
                if ($admin->getId() === $this) {
                    $admin->setId(null);
                }
            }
    
            return $this;
        }

    #[ORM\OneToMany(mappedBy: "id", targetEntity: Client::class)]
    private Collection $clients;

    #[ORM\OneToMany(mappedBy: "id", targetEntity: Conducteur::class)]
    private Collection $conducteurs;

    #[ORM\OneToMany(mappedBy: "id", targetEntity: Organisateur::class)]
    private Collection $organisateurs;
}
