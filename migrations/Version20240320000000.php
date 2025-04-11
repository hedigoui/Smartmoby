<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

final class Version20240320000000 extends AbstractMigration
{
    public function getDescription(): string
    {
        return 'Fix trajet table id column';
    }

    public function up(Schema $schema): void
    {
        $this->addSql('ALTER TABLE trajet MODIFY id INT AUTO_INCREMENT NOT NULL');
    }

    public function down(Schema $schema): void
    {
        $this->addSql('ALTER TABLE trajet MODIFY id INT NOT NULL');
    }
} 