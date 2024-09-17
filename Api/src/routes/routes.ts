
import {Router, Request, Response} from "express";
import { MessageRoutes } from "./message.routes";

export class Routes {


    private routes: Router;

    constructor () {
        this.routes = Router();
    }

    init(): Router {
        this.routes.get("/", (req: Request, res: Response) => {
            res.send({
                message: "Vou chorar"
            });
        });

        this.setRoutes();
        return this.routes;
    }


    private setRoutes() {
        new MessageRoutes(this.routes).setRoutes();
    }

}