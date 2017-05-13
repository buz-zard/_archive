export default (sequelize, DataTypes) => {
  const Questionaire = sequelize.define('Questionaire', {
    name: DataTypes.STRING,
    order: DataTypes.INTEGER,
  });
  return Questionaire;
};
