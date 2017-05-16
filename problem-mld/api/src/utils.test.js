import {validateAnswer} from './utils';


describe('validateAnswer', () => {
  const solution = [{
    id: 1,
    type: 'SINGLE',
    choices: [{id: 2}],
  }, {
    id: 2,
    type: 'SINGLE',
    choices: [{id: 7}],
  }, {
    id: 3,
    type: 'SINGLE',
    choices: [{id: 10}],
  }];


  it('checks that answer is array and is the same length as solution', () => {
    expect(() => validateAnswer(null, solution)).toThrow();
    expect(() => validateAnswer([], solution)).toThrow();
    expect(() => validateAnswer([{
      questionId: 1,
      answer: 2,
    }, {
      questionId: 2,
      answer: 7,
    }], solution)).toThrow();
  });


  it('handles invalid answer object', () => {
    expect(() => validateAnswer([{
      questionId: 1,
      answer: 2,
    }, null, {
      questionId: 3,
      answer: 10,
    }], solution)).toThrow();
  });


  it('handles invalid answer question ids', () => {
    expect(() => validateAnswer([{
      questionId: 1,
      answer: 2,
    }, {
      questionId: 2,
      answer: 7,
    }, {
      questionId: -3,
      answer: 10,
    }], solution)).toThrow();
  });


  it('validates 100% correct answer', () => {
    expect(validateAnswer([{
      questionId: 1,
      answer: 2,
    }, {
      questionId: 2,
      answer: 7,
    }, {
      questionId: 3,
      answer: 10,
    }], solution)).toEqual({mistakes: 0});
  });


  it('validates 100% incorrect answer', () => {
    expect(validateAnswer([{
      questionId: 1,
      answer: 12,
    }, {
      questionId: 2,
      answer: 17,
    }, {
      questionId: 3,
      answer: 110,
    }], solution)).toEqual({mistakes: 3});
  });


  it('validates partially correct answer', () => {
    expect(validateAnswer([{
      questionId: 1,
      answer: 2,
    }, {
      questionId: 2,
      answer: -2,
    }, {
      questionId: 3,
      answer: 10,
    }], solution)).toEqual({mistakes: 1});
  });
});
