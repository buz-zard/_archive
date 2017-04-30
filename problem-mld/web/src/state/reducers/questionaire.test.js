import reducer from './questionaire';
import * as types from '../actions/questionaire';


// #############################################################################
// Questions
// #############################################################################
describe('questions', () => {
  it('sets loading and resets answers', () => {
    expect(reducer({
      questions: {},
      answers: [{
        id: 1,
        value: 2,
      }],
    }, {
      type: types.QUESTIONS_LOADING_STARTED,
    })).toEqual({
      questions: {
        loading: true,
        data: null,
        currentIndex: null,
      },
      answers: [],
    });
  });

  it('finises loading', () => {
    expect(reducer({
      questions: {loading: true},
    }, {
      type: types.QUESTIONS_LOADING_FINISHED,
      payload: [{id: 33}],
    })).toEqual({
      questions: {
        loading: false,
        data: [{id: 33}],
        currentIndex: 0,
      },
    });
  });
});


// #############################################################################
// Answers
// #############################################################################
describe('answers', () => {
  const singleQuestion1 = {
    id: 11,
    type: 'single',
    label: 'singleQuestion1',
    options: [{value: 111, label: 'Option 1'}, {value: 112, label: 'Option 2'}],
  };
  const singleQuestion2 = {
    id: 12,
    type: 'single',
    label: 'singleQuestion2',
    options: [{value: 121, label: 'Option 1'}, {value: 122, label: 'Option 2'}, {value: 123, label: 'Option 3'}],
  };
  const singleQuestion3 = {
    id: 13,
    type: 'single',
    label: 'singleQuestion3',
    options: [{value: 131, label: 'Option 1'}, {value: 132, label: 'Option 2'}],
  };
  const multiQuestion1 = {
    id: 21,
    type: 'multi',
    label: 'multiQuestion1',
    options: [{value: 211, label: 'Option 1'}, {value: 212, label: 'Option 2'}],
  };
  const multiQuestion2 = {
    id: 22,
    type: 'multi',
    label: 'multiQuestion2',
    options: [{value: 221, label: 'Option 1'}, {value: 222, label: 'Option 2'}, {value: 223, label: 'Option 3'}],
  };
  const multiQuestion3 = {
    id: 23,
    type: 'multi',
    label: 'multiQuestion2',
    options: [{value: 231, label: 'Option 1'}, {value: 232, label: 'Option 2'}, {value: 233, label: 'Option 3'}],
  };


  it('prevents saving invalid answer of singe value question', () => {
    // Invalid based on payload (array)
    expect(reducer({
      questions: {
        data: [singleQuestion1, singleQuestion2, singleQuestion3],
        currentIndex: 0,
      },
      answers: [],
    }, {
      type: types.QUESTION_ANSWERED,
      payload: [singleQuestion1.options[1].value],
    })).toEqual({
      questions: {
        data: [singleQuestion1, singleQuestion2, singleQuestion3],
        currentIndex: 0,
      },
      answers: [],
    });
    // Invalid based on payload (null)
    expect(reducer({
      questions: {
        data: [singleQuestion1, singleQuestion2, singleQuestion3],
        currentIndex: 0,
      },
      answers: [],
    }, {
      type: types.QUESTION_ANSWERED,
    })).toEqual({
      questions: {
        data: [singleQuestion1, singleQuestion2, singleQuestion3],
        currentIndex: 0,
      },
      answers: [],
    });
    // Invalid based on payload (option from another question)
    expect(reducer({
      questions: {
        data: [singleQuestion1, singleQuestion2, singleQuestion3],
        currentIndex: 0,
      },
      answers: [],
    }, {
      type: types.QUESTION_ANSWERED,
      payload: singleQuestion2.options[1].value,
    })).toEqual({
      questions: {
        data: [singleQuestion1, singleQuestion2, singleQuestion3],
        currentIndex: 0,
      },
      answers: [],
    });
  });


  it('saves answer of singe value question based on currentIndex and increments it', () => {
    expect(reducer({
      questions: {
        data: [singleQuestion1, singleQuestion2, singleQuestion3],
        currentIndex: 1,
      },
      answers: [{
        questionId: singleQuestion1.id,
        answer: singleQuestion1.options[0].value,
      }],
    }, {
      type: types.QUESTION_ANSWERED,
      payload: singleQuestion2.options[2].value,
    })).toEqual({
      questions: {
        data: [singleQuestion1, singleQuestion2, singleQuestion3],
        currentIndex: 2,
      },
      answers: [{
        questionId: singleQuestion1.id,
        answer: singleQuestion1.options[0].value,
      }, {
        questionId: singleQuestion2.id,
        answer: singleQuestion2.options[2].value,
      }],
    });
  });


  it('saves answer of last singe value question and sets state to answered', () => {
    expect(reducer({
      questions: {
        data: [singleQuestion1, singleQuestion2],
        currentIndex: 1,
      },
      answers: [{
        questionId: singleQuestion1.id,
        answer: singleQuestion1.options[0].value,
      }],
    }, {
      type: types.QUESTION_ANSWERED,
      payload: singleQuestion2.options[2].value,
    })).toEqual({
      questions: {
        data: [singleQuestion1, singleQuestion2],
        currentIndex: 1,
      },
      answers: [{
        questionId: singleQuestion1.id,
        answer: singleQuestion1.options[0].value,
      }, {
        questionId: singleQuestion2.id,
        answer: singleQuestion2.options[2].value,
      }],
    });
  });


  it('prevents saving invalid answer of multi value question', () => {
    // Invalid based on payload (not array)
    expect(reducer({
      questions: {
        data: [multiQuestion1, multiQuestion2, multiQuestion3],
        currentIndex: 0,
      },
      answers: [],
    }, {
      type: types.QUESTION_ANSWERED,
      payload: multiQuestion1.options[1].value,
    })).toEqual({
      questions: {
        data: [multiQuestion1, multiQuestion2, multiQuestion3],
        currentIndex: 0,
      },
      answers: [],
    });
    // Invalid based on payload (empty array)
    expect(reducer({
      questions: {
        data: [multiQuestion1, multiQuestion2, multiQuestion3],
        currentIndex: 0,
      },
      answers: [],
    }, {
      type: types.QUESTION_ANSWERED,
      payload: [],
    })).toEqual({
      questions: {
        data: [multiQuestion1, multiQuestion2, multiQuestion3],
        currentIndex: 0,
      },
      answers: [],
    });
    // Invalid based on payload (option from another question)
    expect(reducer({
      questions: {
        data: [multiQuestion1, multiQuestion2, multiQuestion3],
        currentIndex: 0,
      },
      answers: [],
    }, {
      type: types.QUESTION_ANSWERED,
      payload: [multiQuestion1.options[1].value, multiQuestion2.options[1].value],
    })).toEqual({
      questions: {
        data: [multiQuestion1, multiQuestion2, multiQuestion3],
        currentIndex: 0,
      },
      answers: [],
    });
  });


  it('saves answer of multi value question based on currentIndex and increments it', () => {
    expect(reducer({
      questions: {
        data: [multiQuestion1, multiQuestion2, multiQuestion3],
        currentIndex: 1,
      },
      answers: [{
        questionId: multiQuestion1.id,
        answer: [multiQuestion1.options[0].value],
      }],
    }, {
      type: types.QUESTION_ANSWERED,
      payload: [multiQuestion2.options[0].value, multiQuestion2.options[1].value],
    })).toEqual({
      questions: {
        data: [multiQuestion1, multiQuestion2, multiQuestion3],
        currentIndex: 2,
      },
      answers: [{
        questionId: multiQuestion1.id,
        answer: [multiQuestion1.options[0].value],
      }, {
        questionId: multiQuestion2.id,
        answer: [multiQuestion2.options[0].value, multiQuestion2.options[1].value],
      }],
    });
  });


  it('saves answer of last multi value question and sets state to answered', () => {
    expect(reducer({
      questions: {
        data: [multiQuestion1, multiQuestion2],
        currentIndex: 1,
      },
      answers: [{
        questionId: multiQuestion1.id,
        answer: [multiQuestion1.options[0].value],
      }],
    }, {
      type: types.QUESTION_ANSWERED,
      payload: [multiQuestion2.options[2].value],
    })).toEqual({
      questions: {
        data: [multiQuestion1, multiQuestion2],
        currentIndex: 1,
      },
      answers: [{
        questionId: multiQuestion1.id,
        answer: [multiQuestion1.options[0].value],
      }, {
        questionId: multiQuestion2.id,
        answer: [multiQuestion2.options[2].value],
      }],
    });
  });
});

// #############################################################################
// Questionaire
// #############################################################################
describe('questionaire', () => {
  const singleQuestion1 = {
    id: 11,
    type: 'single',
    label: 'singleQuestion1',
    options: [{value: 111, label: 'Option 1'}, {value: 112, label: 'Option 2'}],
  };
  const singleQuestion2 = {
    id: 12,
    type: 'single',
    label: 'singleQuestion2',
    options: [{value: 121, label: 'Option 1'}, {value: 122, label: 'Option 2'}, {value: 123, label: 'Option 3'}],
  };

  it('resets state after SUBMITTED action', () => {
    expect(reducer({
      questions: {
        loading: false,
        data: [singleQuestion1, singleQuestion2],
        currentIndex: 1,
      },
      answers: [{
        questionId: singleQuestion1.id,
        answer: singleQuestion1.options[0].value,
      }, {
        questionId: singleQuestion2.id,
        answer: singleQuestion2.options[1].value,
      }],
      completed: true,
    }, {
      type: types.SUBMITTED,
    })).toEqual({
      questions: {
        loading: false,
        data: [singleQuestion1, singleQuestion2],
        currentIndex: 0,
      },
      answers: [],
      completed: false,
    });
  });


  it('doesn\'t resets with SUBMITTED action if questionaire isn\'t completed', () => {
    expect(reducer({
      questions: {
        loading: false,
        data: [singleQuestion1, singleQuestion2],
        currentIndex: 1,
      },
      answers: [{
        questionId: singleQuestion1.id,
        answer: singleQuestion1.options[0].value,
      }, {
        questionId: singleQuestion2.id,
        answer: singleQuestion2.options[1].value,
      }],
      completed: false,
    }, {
      type: types.SUBMITTED,
    })).toEqual({
      questions: {
        loading: false,
        data: [singleQuestion1, singleQuestion2],
        currentIndex: 1,
      },
      answers: [{
        questionId: singleQuestion1.id,
        answer: singleQuestion1.options[0].value,
      }, {
        questionId: singleQuestion2.id,
        answer: singleQuestion2.options[1].value,
      }],
      completed: false,
    });
  });
});
