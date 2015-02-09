---

<p align="center">
  <img src="http://upload.wikimedia.org/wikipedia/en/a/a9/Heroku_logo.png" height="30%" width="30%"/>
  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
  <img src="http://upload.wikimedia.org/wikipedia/commons/1/1d/AmazonWebservices_Logo.svg"  height="22%" width="22%">
</p>


# *Programming in Cloud Computing* Project

#### Task:
 Create a project where 1 or more cloud services would be used togerther.

#### Solution:
A primitive news [web portal][1], where authors, articles and pictures are added through [admin console][2] and displayed in the main page.

* The website is hosted at **[Heroku][heroku] cloud hosting platform**.
* All the uploaded images and image thumbnails are sent to **[Amazon S3][s3] cloud storage service** and displayed directly from Amazon repository in the main page.

#### Other:

Technology stack:
* Python 3.4
* Django 1.7.1 framework
* [Other small libs][3]

`Fabric` commands for local development:
* `install` - create Python virtual environment and install required dependecies.
* `deploy` - deploy code changes directly to Heroku cloud hosting platform.
* `go` - start local development server.
* `freeze` - freeze installed dependencies to `requirements.txt` file.


[Admin console][2] credentials:
> username - `tester`

> password -`tester`

---

[1]: https://mysterious-taiga-3717.herokuapp.com/
[2]: https://mysterious-taiga-3717.herokuapp.com/admin
[3]: /cloud-paper-news/requirements.txt
[heroku]: https://www.heroku.com/
[s3]: http://aws.amazon.com/s3/
