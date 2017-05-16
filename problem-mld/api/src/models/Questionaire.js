export default (sequelize, DataTypes) => {
  const Questionaire = sequelize.define('Questionaire', {
    name: DataTypes.STRING,
    order: DataTypes.INTEGER,
  });

  Questionaire.associate = (models) => {
    Questionaire.hasMany(models.Question, {onDelete: 'cascade', as: 'questions', foreignKey: 'questionaireId'});
  };

  return Questionaire;
};
