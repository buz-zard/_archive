import express from 'express';
import bodyParser from 'body-parser';
import morgan from 'morgan';

import config from './config.json';
import {
  questionaires as questionairesController,
  questions as questionsController,
  answers as answersController,
} from './controllers';


const app = express();

app.use(morgan('dev'));
app.use(bodyParser.json());


app.get('/', (req, res) => {
  res.send('Hello World!');
});


app.route(`${config.apiPrefix}/questionaires`)
  .get(questionairesController.getList);

app.route(`${config.apiPrefix}/questionaires/:id`)
  .get(questionairesController.getOneById);

app.route(`${config.apiPrefix}/questionaires/:questionaireId/questions`)
  .get(questionsController.getListForQuestionaire);

app.route(`${config.apiPrefix}/questionaires/:questionaireId/answers`)
  .post(answersController.submitAnswer);


app.listen(config.port, () => {
  console.log(`Api server started on port ${config.port}!`);
});


export default app;
