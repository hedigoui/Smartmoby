<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20240320000001 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Add conducteur_id column to vehicule table';
    }

    public function up(Schema $schema): void
    {
        $this->addSql('ALTER TABLE vehicule ADD conducteur_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE vehicule ADD CONSTRAINT FK_292FFF1DF16F4AC6 FOREIGN KEY (conducteur_id) REFERENCES conducteur (id)');
        $this->addSql('CREATE INDEX IDX_292FFF1DF16F4AC6 ON vehicule (conducteur_id)');
    }

    public function down(Schema $schema): void
    {
        $this->addSql('ALTER TABLE vehicule DROP FOREIGN KEY FK_292FFF1DF16F4AC6');
        $this->addSql('DROP INDEX IDX_292FFF1DF16F4AC6 ON vehicule');
        $this->addSql('ALTER TABLE vehicule DROP conducteur_id');
    }
} 