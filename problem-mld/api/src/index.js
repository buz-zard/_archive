import express from 'express';
import bodyParser from 'body-parser';
import morgan from 'morgan';
// import mysql from 'mysql';

import config from './config';
import {
  questionaires as questionairesController,
  questions as questionsController,
  answers as answersController,
} from './controllers';


const app = express();

app.use(morgan('dev'));
app.use(bodyParser.json());

// switch (process.env.NODE_ENV) {
//   case 'production':
//     app.set('connection', mysql.createConnection({
//       host: process.env.RDS_HOSTNAME,
//       user: process.env.RDS_USERNAME,
//       password: process.env.RDS_PASSWORD,
//       port: process.env.RDS_PORT,
//     }));
//     break;
//   default:
//     break;
// }


app.get('/', (req, res) => {
  res.send('API');
});


app.route(`${config.apiPrefix}/questionaires`)
  .get(questionairesController.getList);

app.route(`${config.apiPrefix}/questionaires/:id`)
  .get(questionairesController.getOneById);

app.route(`${config.apiPrefix}/questionaires/:questionaireId/questions`)
  .get(questionsController.getListForQuestionaire);

app.route(`${config.apiPrefix}/questionaires/:questionaireId/answers`)
  .post(answersController.submitAnswer);


app.listen(process.env.PORT || config.port, () => {
  console.log(`API server started on port ${process.env.PORT || config.port}!`);
});


export default app;
