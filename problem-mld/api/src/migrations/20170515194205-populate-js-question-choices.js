module.exports = {
  up(queryInterface) {
    const findQuestion = (questionaireId, name) => queryInterface
      .select(queryInterface.sequelize.models.Question, 'Questions', {where: {questionaireId, name}});

    const newQuestion = (order, questionId, name, isCorrect = false) => ({
      order,
      questionId,
      name,
      isCorrect,
      createdAt: new Date(),
      updatedAt: new Date(),
    });

    return queryInterface
      .select(queryInterface.sequelize.models.Questionaire, 'Questionaires', {where: {name: 'JavaScript newbie'}})
      .then((data) => {
        const questionaireId = data[0].id;
        return Promise.all([
          findQuestion(questionaireId, 'Inside which HTML element do we put the JavaScript?')
            .then((questions) => {
              const questionId = questions[0].id;
              return queryInterface.bulkInsert('Choices', [
                newQuestion(1, questionId, '<javascript>'),
                newQuestion(2, questionId, '<script>', true),
                newQuestion(3, questionId, '<js>'),
                newQuestion(4, questionId, '<scripting>'),
              ]);
            }),
          findQuestion(questionaireId, 'Where is the correct place to insert a JavaScript?')
            .then((questions) => {
              const questionId = questions[0].id;
              return queryInterface.bulkInsert('Choices', [
                newQuestion(1, questionId, 'The <head> section'),
                newQuestion(2, questionId, 'Both the <head> section and the <body> section are correct'),
                newQuestion(3, questionId, 'The <body> section', true),
              ]);
            }),
          findQuestion(questionaireId, 'What is the correct syntax for referring to an external script called "xxx.js"?') // eslint-disable-line
            .then((questions) => {
              const questionId = questions[0].id;
              return queryInterface.bulkInsert('Choices', [
                newQuestion(1, questionId, '<script src="xxx.js">'),
                newQuestion(2, questionId, '<script href="xxx.js">'),
                newQuestion(3, questionId, '<script name="xxx.js">', true),
              ]);
            }),
        ]);
      }, {});
  },
  down(queryInterface) {
    return queryInterface.bulkDelete('Questions', null, {});
  },
};
