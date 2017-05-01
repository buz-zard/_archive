export default {
  questionaires: [{
    id: 1,
    label: 'JS Quiz',
  }, {
    id: 2,
    label: 'Java Quiz',
  }],
  questions: [{
    questionaireId: 1,
    data: [{
      id: 11,
      type: 'single',
      label: 'Inside which HTML element do we put the JavaScript?',
      options: [{
        value: 111,
        label: '<scripting>',
      }, {
        value: 112,
        label: '<script>',
      }, {
        value: 113,
        label: '<javascript>',
      }, {
        value: 114,
        label: '<js>',
      }],
    }, {
      id: 12,
      type: 'multi',
      label: 'Which of the following is an advantage of using JavaScript?',
      options: [{
        value: 121,
        label: 'Less server interaction',
      }, {
        value: 122,
        label: 'Immediate feedback to the visitors',
      }, {
        value: 123,
        label: 'Increased interactivity',
      }, {
        value: 124,
        label: 'None',
      }],
    }, {
      id: 13,
      type: 'single',
      label: 'Which built-in method reverses the order of the elements of an array?',
      options: [{
        value: 131,
        label: 'changeOrder(order)',
      }, {
        value: 132,
        label: 'reverse()',
      }, {
        value: 133,
        label: 'sort(order)',
      }, {
        value: 134,
        label: 'None of the above.',
      }],
    }],
  }, {
    questionaireId: 2,
    data: [{
      id: 21,
      type: 'single',
      label: 'Objects are stored on Stack.',
      options: [{
        value: 211,
        label: 'true',
      }, {
        value: 212,
        label: 'false',
      }],
    }, {
      id: 22,
      type: 'single',
      label: 'What is the default value of char variable?',
      options: [{
        value: 221,
        label: '\'\\u0000\'',
      }, {
        value: 222,
        label: '0',
      }, {
        value: 223,
        label: 'null',
      }, {
        value: 224,
        label: 'not defined',
      }],
    }],
  }],
};
