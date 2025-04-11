<?php

namespace App\Form;

use App\Entity\Vehicule;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents;
use Symfony\Component\OptionsResolver\OptionsResolver;

class VehiculeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder

            ->add('type', TextType::class, [
                'label' => 'Type',
                'required' => true,
                'attr' => ['maxlength' => 50],
            ])
            ->add('capacite', IntegerType::class, [
                'label' => 'Capacity',
                'required' => true,
            ])
            ->add('statut', ChoiceType::class, [
                'label' => 'Status',
                'required' => true,
                'choices' => [
                    'Available' => 'available',
                    'In Use' => 'in_use',
                    'Under Maintenance' => 'under_maintenance',
                ],
            ])
            ->add('dispo', ChoiceType::class, [
                'label' => 'Available',
                'required' => true,
                'choices' => [
                    'Yes' => true,
                    'No' => false,
                ],
                'expanded' => true,
                'multiple' => false,
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Vehicule::class,
        ]);
    }
} 