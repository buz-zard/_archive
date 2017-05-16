module.exports = {
  up(queryInterface) {
    return queryInterface
      .select(queryInterface.sequelize.models.Questionaire, 'Questionaires', {where: {name: 'HTML starter'}})
      .then((data) => {
        const questionaireId = data[0].id;
        const newQuestion = (order, name, type = 'SINGLE') => ({
          order,
          name,
          type,
          questionaireId,
          createdAt: new Date(),
          updatedAt: new Date(),
        });

        return queryInterface.bulkInsert('Questions', [
          newQuestion(1, 'What does HTML stand for?'),
          newQuestion(2, 'Who is making the Web standards?'),
          newQuestion(3, 'Choose the correct HTML element for the largest heading:'),
          newQuestion(4, 'What is the correct HTML element for inserting a line break?'),
          newQuestion(5, 'What is the correct HTML for adding a background color?'),
        ], {});
      });
  },
  down() {
    return Promise.reject();
  },
};
