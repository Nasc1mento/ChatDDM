import { IDatabaseConnection } from "./databases/database.connection.interface";
import express, {Express} from "express";
import { MongoConnection } from "./databases/mongo.database.connection";
import { Routes } from "./routes/routes";
import { env } from "./env";

class Server {

    private app: Express;
    private database: IDatabaseConnection;

    constructor() {
        this.app = express();
        this.database = new MongoConnection();
    }

    init(): void {
        this.configDatabase();
        this.configMiddleware();
        this.configRoutes();

        this.app.listen(env.PORT, () => {
            console.log("running");
        });
    }


    private configMiddleware(): void {
        this.app.use(express.json());
    }

    private configDatabase(): void {
        this.database.connect();
    }

    private configRoutes(): void {
        this.app.use("/api", new Routes().init());
    }
}


new Server().init();