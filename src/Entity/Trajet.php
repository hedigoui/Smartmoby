<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;


#[ORM\Entity]
class Trajet
{

    #[ORM\Id]
    #[ORM\Column(type: "integer")]
    private int $id;

    #[ORM\Column(type: "string", length: 100)]
    private string $point_d;

    #[ORM\Column(type: "string", length: 100)]
    private string $point_a;

    #[ORM\Column(type: "datetime")]
    private \DateTimeInterface $date_d;

    #[ORM\Column(type: "datetime")]
    private \DateTimeInterface $date_a;

    #[ORM\Column(type: "float")]
    private float $distance;

    #[ORM\Column(type: "float")]
    private float $prix;

    #[ORM\Column(type: "integer")]
    private int $id_veh;

    public function getId()
    {
        return $this->id;
    }

    public function setId($value)
    {
        $this->id = $value;
    }

    public function getPoint_d()
    {
        return $this->point_d;
    }

    public function setPoint_d($value)
    {
        $this->point_d = $value;
    }

    public function getPoint_a()
    {
        return $this->point_a;
    }

    public function setPoint_a($value)
    {
        $this->point_a = $value;
    }

    public function getDate_d()
    {
        return $this->date_d;
    }

    public function setDate_d($value)
    {
        $this->date_d = $value;
    }

    public function getDate_a()
    {
        return $this->date_a;
    }

    public function setDate_a($value)
    {
        $this->date_a = $value;
    }

    public function getDistance()
    {
        return $this->distance;
    }

    public function setDistance($value)
    {
        $this->distance = $value;
    }

    public function getPrix()
    {
        return $this->prix;
    }

    public function setPrix($value)
    {
        $this->prix = $value;
    }

    public function getId_veh()
    {
        return $this->id_veh;
    }

    public function setId_veh($value)
    {
        $this->id_veh = $value;
    }
}
