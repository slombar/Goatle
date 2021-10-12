 // Load dependencies
    const express = require('express')
    const bodyParser = require('body-parser')
    const app = express()

    // App middlewares
    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({ extended: false }));

    // Initialize Pusher
    var Pusher = require('pusher');
    var pusher = new Pusher({
        appId: '1280705',
        key: 'b65f2d2ca63e8f87d4e8',
        secret: '310bf9e2e27e640d372c',
        cluster: 'mt1',
        encrypted: true
    });

    // app's routes
    app.post('/update-status', (req, res) => {

        pusher.trigger('Goatle', 'my-event', {
            "message": req.query.status
        });

        res.json(req.query.status)

    })

    app.get('/', (req, res, next) => {
        res.json("Server is Running")
    })

    app.listen(3000, () => console.log('Running application...'))