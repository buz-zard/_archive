module.exports = {
  up(queryInterface, Sequelize) {
    return queryInterface.createTable('Questions', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER,
      },
      name: {
        type: Sequelize.STRING,
        allowNull: false,
      },
      order: {
        type: Sequelize.INTEGER,
        allowNull: false,
      },
      type: {
        type: Sequelize.ENUM('SINGLE'),
        defaultValue: 'SINGLE',
        allowNull: false,
      },
      questionaireId: {
        allowNull: false,
        type: Sequelize.INTEGER,
        references: {
          model: 'Questionaires',
          key: 'id',
        },
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE,
      },
      updatedAt: {
        allowNull: false,
        type: Sequelize.DATE,
      },
    });
  },
  down(queryInterface) {
    return queryInterface.dropTable('Questions');
  },
};
