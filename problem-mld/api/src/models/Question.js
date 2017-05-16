import {Questionaire} from './';


export default (sequelize, DataTypes) => {
  const Question = sequelize.define('Question', {
    name: DataTypes.STRING,
    order: DataTypes.INTEGER,
    type: {
      type: DataTypes.ENUM('SINGLE'),
      defaultValue: 'SINGLE',
      allowNull: false,
    },
    questionaireId: {
      type: DataTypes.INTEGER,
      model: Questionaire,
      key: 'id',
    },
  });
  return Question;
};
