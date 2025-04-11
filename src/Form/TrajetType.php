<?php

namespace App\Form;

use App\Entity\Trajet;
use App\Entity\Vehicule;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class TrajetType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('vehicule', EntityType::class, [
                'class' => Vehicule::class,
                'choice_label' => 'type',
                'label' => 'Vehicle',
                'placeholder' => 'Select a vehicle',
                'required' => true,
            ])
            ->add('pointDepart', TextType::class, [
                'label' => 'Departure Point',
                'required' => true,
            ])
            ->add('pointArrivee', TextType::class, [
                'label' => 'Arrival Point',
                'required' => true,
            ])
            ->add('dateDepart', DateTimeType::class, [
                'label' => 'Departure Date',
                'required' => true,
                'widget' => 'single_text',
                'html5' => true,
            ])
            ->add('dateArrivee', DateTimeType::class, [
                'label' => 'Arrival Date',
                'required' => true,
                'widget' => 'single_text',
                'html5' => true,
            ])
            ->add('distance', NumberType::class, [
                'label' => 'Distance (km)',
                'required' => true,
                'scale' => 2,
            ])
            ->add('prix', NumberType::class, [
                'label' => 'Price (€)',
                'required' => true,
                'scale' => 2,
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Trajet::class,
        ]);
    }
} 