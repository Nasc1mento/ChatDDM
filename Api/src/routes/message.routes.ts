import { Router, Request, Response } from "express";
import { MessageController } from "../controllers/message.controller";



export class MessageRoutes {


    private router: Router;
    private messageController: MessageController;

    constructor(routes: Router) {
        this.router = routes;
        this.messageController = new MessageController();
    }

    setRoutes(): void {
        this.router.post("/messages", (req: Request, res: Response) => {
            this.messageController.create(req, res);
        })

        this.router.get("/messages", (req: Request, res: Response) => {
            this.messageController.getAll(req, res);
        })
    }
}