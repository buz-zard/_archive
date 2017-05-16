import reducer from './questionaire';
import * as types from '../actions/questionaire';


// #############################################################################
// Generic actions
// #############################################################################
describe('generic actions', () => {
  it('initializes', () => {
    expect(reducer({
      id: 253,
      questions: {},
      answers: [{
        id: 1,
        value: 2,
      }],
    }, {
      type: types.INITIALIZED,
      payload: 1,
    })).toEqual({
      id: 1,
      info: null,
      questions: {
        loading: false,
        data: null,
        currentIndex: null,
      },
      answers: [],
      completed: false,
    });
  });


  it('sets metadata', () => {
    expect(reducer({
      id: 1,
      questions: {},
      answers: [],
    }, {
      type: types.METADATA_LOADING_FINISHED,
      payload: {id: 1, data: {name: 'JavaScrip quiz level 99'}},
    })).toEqual({
      id: 1,
      info: {name: 'JavaScrip quiz level 99'},
      questions: {},
      answers: [],
    });
  });
});


// #############################################################################
// Questions
// #############################################################################
describe('questions', () => {
  it('sets loading true', () => {
    expect(reducer({
      id: 253,
      questions: {},
      answers: [{
        id: 1,
        value: 2,
      }],
    }, {
      type: types.QUESTIONS_LOADING_STARTED,
      payload: 253,
    })).toEqual({
      id: 253,
      questions: {
        loading: true,
        data: null,
        currentIndex: null,
      },
      answers: [{
        id: 1,
        value: 2,
      }],
    });
  });


  it('finises loading with right questionaire id', () => {
    expect(reducer({
      id: 67,
      questions: {loading: true},
    }, {
      type: types.QUESTIONS_LOADING_FINISHED,
      payload: {id: 67, questions: [{id: 33}]},
    })).toEqual({
      id: 67,
      questions: {
        loading: false,
        data: [{id: 33}],
        currentIndex: 0,
      },
    });
  });


  it('doesn\'t set data if id is wrong', () => {
    expect(reducer({
      id: 67,
      questions: {loading: true},
    }, {
      type: types.QUESTIONS_LOADING_FINISHED,
      payload: {id: 76, questions: [{id: 33}]},
    })).toEqual({
      id: 67,
      questions: {
        loading: true,
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
    type: 'SINGLE',
    name: 'singleQuestion1',
    options: [{value: 111, name: 'Option 1'}, {value: 112, name: 'Option 2'}],
  };
  const singleQuestion2 = {
    id: 12,
    type: 'SINGLE',
    name: 'singleQuestion2',
    options: [{value: 121, name: 'Option 1'}, {value: 122, name: 'Option 2'}, {value: 123, name: 'Option 3'}],
  };
  const singleQuestion3 = {
    id: 13,
    type: 'SINGLE',
    name: 'singleQuestion3',
    options: [{value: 131, name: 'Option 1'}, {value: 132, name: 'Option 2'}],
  };
  const multiQuestion1 = {
    id: 21,
    type: 'MULTI',
    name: 'multiQuestion1',
    options: [{value: 211, name: 'Option 1'}, {value: 212, name: 'Option 2'}],
  };
  const multiQuestion2 = {
    id: 22,
    type: 'MULTI',
    name: 'multiQuestion2',
    options: [{value: 221, name: 'Option 1'}, {value: 222, name: 'Option 2'}, {value: 223, name: 'Option 3'}],
  };
  const multiQuestion3 = {
    id: 23,
    type: 'MULTI',
    name: 'multiQuestion2',
    options: [{value: 231, name: 'Option 1'}, {value: 232, name: 'Option 2'}, {value: 233, name: 'Option 3'}],
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
    type: 'SINGLE',
    name: 'singleQuestion1',
    options: [{value: 111, name: 'Option 1'}, {value: 112, name: 'Option 2'}],
  };
  const singleQuestion2 = {
    id: 12,
    type: 'SINGLE',
    name: 'singleQuestion2',
    options: [{value: 121, name: 'Option 1'}, {value: 122, name: 'Option 2'}, {value: 123, name: 'Option 3'}],
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
