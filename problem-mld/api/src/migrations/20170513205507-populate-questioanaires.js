module.exports = {
  up(queryInterface) {
    return queryInterface.bulkInsert('Questionaires', [{
      order: 1,
      name: 'HTML starter',
      createdAt: new Date(),
      updatedAt: new Date(),
    }, {
      order: 2,
      name: 'JavaScript newbie',
      createdAt: new Date(),
      updatedAt: new Date(),
    }], {});
  },
  down(queryInterface) {
    return queryInterface.bulkDelete('Questionaires', null, {});
  },
};
