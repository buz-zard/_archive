module.exports = {
  up(queryInterface) {
    let questionaireId;
    let questionId;

    const findQuestion = name => queryInterface
      .select(queryInterface.sequelize.models.Question, 'Questions', {where: {questionaireId, name}});

    const findChoice = name => queryInterface
      .select(queryInterface.sequelize.models.Choice, 'Choices', {where: {questionId, name}});

    return queryInterface
      .select(queryInterface.sequelize.models.Questionaire, 'Questionaires', {where: {name: 'JavaScript newbie'}})
      .then((data) => {
        questionaireId = data[0].id;
        return findQuestion('What is the correct syntax for referring to an external script called "xxx.js"?');
      })
      .then((data) => {
        questionId = data[0].id;
        return Promise.all([
          findChoice('<script src="xxx.js">'),
          findChoice('<script name="xxx.js">'),
        ]);
      })
      .then((data) => {
        const choice1 = data[0][0].id;
        const choice2 = data[1][0].id;
        return Promise.all([
          queryInterface.bulkUpdate('Choices', {isCorrect: true}, {id: choice1}),
          queryInterface.bulkUpdate('Choices', {isCorrect: false}, {id: choice2}),
        ]);
      });
  },
  down() {
    return Promise.reject();
  },
};
