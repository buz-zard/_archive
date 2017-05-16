module.exports = {
  up(queryInterface) {
    return queryInterface
      .select(queryInterface.sequelize.models.Questionaire, 'Questionaires', {where: {name: 'JavaScript newbie'}})
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
          newQuestion(1, 'Inside which HTML element do we put the JavaScript?'),
          newQuestion(2, 'Where is the correct place to insert a JavaScript?'),
          newQuestion(3, 'What is the correct syntax for referring to an external script called "xxx.js"?'),
        ], {});
      });
  },
  down(queryInterface) {
    return queryInterface.bulkDelete('Questions', null, {});
  },
};
