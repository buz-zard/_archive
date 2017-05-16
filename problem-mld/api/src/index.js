import express from 'express';
import http from 'http';
import bodyParser from 'body-parser';
import errorHandler from 'express-error-handler';
import morgan from 'morgan';

import config from './config';
import {
  questionaires as questionairesController,
  questions as questionsController,
  answers as answersController,
} from './controllers';

const app = express();
const server = http.createServer(app);

app.use(morgan('dev'));
app.use(bodyParser.json());
app.use(errorHandler({server}));


app.get('/', (req, res) => {
  res.send(`API [${process.env.NODE_ENV || 'development'}]`);
});


app.route(`${config.apiPrefix}/questionaires`)
  .get(questionairesController.getList);

app.route(`${config.apiPrefix}/questionaires/:id`)
  .get(questionairesController.getOneById);

app.route(`${config.apiPrefix}/questionaires/:questionaireId/questions`)
  .get(questionsController.getListForQuestionaire);

app.route(`${config.apiPrefix}/questionaires/questions/:questionId/choices`)
  .get(questionsController.getChoicesListForQuestion);

app.route(`${config.apiPrefix}/questionaires/:questionaireId/answers`)
  .post(answersController.submitAnswer);


server.listen(process.env.PORT || config.port, () => {
  console.log(`\nAPI server started on port - ${process.env.PORT || config.port}\n`); // eslint-disable-line
});


export default server;
