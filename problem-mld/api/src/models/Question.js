export default (sequelize, DataTypes) => {
  const Question = sequelize.define('Question', {
    name: DataTypes.STRING,
    order: DataTypes.INTEGER,
    type: {
      type: DataTypes.ENUM('SINGLE'),
      defaultValue: 'SINGLE',
      allowNull: false,
    },
  });

  Question.associate = (models) => {
    Question.hasMany(models.Choice, {onDelete: 'cascade', as: 'choices', foreignKey: 'questionId'});
  };

  return Question;
};
