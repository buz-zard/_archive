export default (sequelize, DataTypes) => {
  const Questionaire = sequelize.define('Questionaire', {
    name: DataTypes.STRING,
    order: DataTypes.INTEGER,
  }, {
    associate(models) {
      Questionaire.hasMany(models.Question, {onDelete: 'cascade'});
    },
  });
  return Questionaire;
};
