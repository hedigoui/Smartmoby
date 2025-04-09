<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

use App\Entity\Evenment;

#[ORM\Entity]
class Fedback
{

    #[ORM\Id]
    #[ORM\Column(type: "integer")]
    private int $id;

        #[ORM\ManyToOne(targetEntity: Evenment::class, inversedBy: "fedbacks")]
    #[ORM\JoinColumn(name: 'id_event', referencedColumnName: 'id_event', onDelete: 'CASCADE')]
    private Evenment $id_event;

    #[ORM\Column(type: "string", length: 250)]
    private string $commentaire;

    #[ORM\Column(type: "integer")]
    private int $note;

    public function getId()
    {
        return $this->id;
    }

    public function setId($value)
    {
        $this->id = $value;
    }

    public function getId_event()
    {
        return $this->id_event;
    }

    public function setId_event($value)
    {
        $this->id_event = $value;
    }

    public function getCommentaire()
    {
        return $this->commentaire;
    }

    public function setCommentaire($value)
    {
        $this->commentaire = $value;
    }

    public function getNote()
    {
        return $this->note;
    }

    public function setNote($value)
    {
        $this->note = $value;
    }
}
