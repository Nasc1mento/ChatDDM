const WebSocket = require("ws");
const { v4: uuidv4 } = require('uuid');
const server = new WebSocket.Server({
    port: 3000
});

const connections = [];


server.on("connection", connection => {
    connection.on("message", message => {
        const parsed = JSON.parse(message);

        if (parsed.type == "join") {
            connection.id = uuidv4();
            connection.name = parsed.name;
            connections.push(connection);
            
            // server.sendBroadcast(JSON.stringify({
            //     "list": connections.map(c => ({ id: c.id, name: c.name }))
            // }));

        // } else if(parsed.type == "connections") {
        //     connection.send(JSON.stringify({
        //         "list": connections.map(c => ({ id: c.id, name: c.name }))
        //     }));

        } else {
            server.sendBroadcastEx(connection, JSON.stringify(parsed).toString("utf-8"));
        }
    });


    connection.on("close", () => {
        connections.splice(connections.indexOf(connection), 1);

        // server.sendBroadcast(JSON.stringify({
        //     "type": "left",
        //     "list": connections.map(c => ({ id: c.id, name: c.name }))
        // }));
        
    });
})


server.sendBroadcast = data => {
    connections.forEach(element => {
        element.send(data);
    });
}

server.sendBroadcastEx = (sender, data) => {
    connections.forEach(element => {
        if (element != sender)
            element.send(data);
    });
}






