import {questionaires} from '../services';


const onError = res => err => res.status(500).send(err);


export default {
  getList(req, res) {
    questionaires.getList().then((response) => {
      res.json(response);
    }).catch(onError(res));
  },
  getOneById(req, res) {
    questionaires.getOne(parseInt(req.params.id, 10)).then((response) => {
      res.json(response);
    }).catch(onError(res));
  },
};

