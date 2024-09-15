const WebSocket = require("ws");
const server = new WebSocket.Server({
    port: 3000
});

let connections = []

server.on("connection", connection => {
    console.log("new connection");
    connections.push(connection);

    connection.on("message", message => {
        connections.forEach(element => {
            if(element != connection)
                element.send(message.toString("utf-8"));
        });
    });

    connection.on("close", () => {
        console.log("connection closed");
        connections.splice(connections.indexOf(connection), 1);
    });
})






