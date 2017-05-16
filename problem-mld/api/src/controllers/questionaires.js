import {questionaires} from '../services';


export default {
  getList(req, res, next) {
    questionaires.getList().then((response) => {
      res.json(response);
    }).catch(next);
  },
  getOneById(req, res, next) {
    questionaires.getOne(parseInt(req.params.id, 10)).then((response) => {
      res.json(response);
    }).catch(next);
  },
};

