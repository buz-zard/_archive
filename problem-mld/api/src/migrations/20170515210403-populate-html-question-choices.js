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
      .select(queryInterface.sequelize.models.Questionaire, 'Questionaires', {where: {name: 'HTML starter'}})
      .then((data) => {
        const questionaireId = data[0].id;
        return Promise.all([
          findQuestion(questionaireId, 'What does HTML stand for?')
            .then((questions) => {
              const questionId = questions[0].id;
              return queryInterface.bulkInsert('Choices', [
                newQuestion(1, questionId, 'Home Tool Markup Language'),
                newQuestion(2, questionId, 'Hyperlinks and Text Markup Language'),
                newQuestion(3, questionId, 'Hyper Text Markup Language', true),
              ]);
            }),
          findQuestion(questionaireId, 'Who is making the Web standards?')
            .then((questions) => {
              const questionId = questions[0].id;
              return queryInterface.bulkInsert('Choices', [
                newQuestion(1, questionId, 'Google'),
                newQuestion(2, questionId, 'The World Wide Web Consortium', true),
                newQuestion(3, questionId, 'Mozille'),
                newQuestion(4, questionId, 'Microsoft'),
              ]);
            }),
          findQuestion(questionaireId, 'Choose the correct HTML element for the largest heading:')
            .then((questions) => {
              const questionId = questions[0].id;
              return queryInterface.bulkInsert('Choices', [
                newQuestion(1, questionId, '<heading>'),
                newQuestion(2, questionId, '<head>'),
                newQuestion(3, questionId, '<h6>'),
                newQuestion(4, questionId, '<h1>', true),
              ]);
            }),
          findQuestion(questionaireId, 'What is the correct HTML element for inserting a line break?')
            .then((questions) => {
              const questionId = questions[0].id;
              return queryInterface.bulkInsert('Choices', [
                newQuestion(1, questionId, '<break>'),
                newQuestion(1, questionId, '<lb>'),
                newQuestion(1, questionId, '<br>', true),
              ]);
            }),
          findQuestion(questionaireId, 'What is the correct HTML for adding a background color?')
            .then((questions) => {
              const questionId = questions[0].id;
              return queryInterface.bulkInsert('Choices', [
                newQuestion(1, questionId, '<body bg="yellow">'),
                newQuestion(1, questionId, '<body style="background-color:yellow;">', true),
                newQuestion(1, questionId, '<background>yellow</background>'),
              ]);
            }),
        ]);
      }, {});
  },
  down(queryInterface) {
    return queryInterface.bulkDelete('Questions', null, {});
  },
};
