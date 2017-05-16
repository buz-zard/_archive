import {Question} from './';


export default (sequelize, DataTypes) => {
  const Choice = sequelize.define('Choice', {
    name: DataTypes.STRING,
    order: DataTypes.INTEGER,
    isCorrect: {
      type: DataTypes.BOOLEAN,
      defaultValue: false,
      allowNull: false,
    },
    questionId: {
      type: DataTypes.INTEGER,
      model: Question,
      key: 'id',
    },
  });

  Choice.associate = (models) => {
    Choice.belongsTo(models.Question, {foreignKey: 'questionId'});
  };

  return Choice;
};
