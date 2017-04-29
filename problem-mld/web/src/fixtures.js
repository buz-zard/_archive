export default {
  questions: [{
    id: 1,
    type: 'single',
    label: 'Inside which HTML element do we put the JavaScript?',
    options: [{
      value: 1,
      label: '<scripting>',
    }, {
      value: 2,
      label: '<script>',
    }, {
      value: 3,
      label: '<javascript>',
    }, {
      value: 4,
      label: '<js>',
    }],
  }, {
    id: 2,
    type: 'multi',
    label: 'Which of the following is an advantage of using JavaScript?',
    options: [{
      value: 1,
      label: 'Less server interaction',
    }, {
      value: 2,
      label: 'Immediate feedback to the visitors',
    }, {
      value: 3,
      label: 'Increased interactivity',
    }, {
      value: 4,
      label: 'None',
    }],
  }, {
    id: 3,
    type: 'sinle',
    label: 'Which built-in method reverses the order of the elements of an array?',
    options: [{
      value: 1,
      label: 'changeOrder(order)',
    }, {
      value: 2,
      label: 'reverse()',
    }, {
      value: 3,
      label: 'sort(order)',
    }, {
      value: 4,
      label: 'None of the above.',
    }],
  }],
};
