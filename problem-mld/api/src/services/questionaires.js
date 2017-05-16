import {Questionaire} from '../models';


export default {
  getList() {
    return Questionaire.findAll({order: [['order', 'ASC']]});
  },
  getOne(id) {
    return Questionaire.findById(id);
  },
};
