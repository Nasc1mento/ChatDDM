import { MessageRepository } from "../repositories/message.repository";
import { Message } from "../model/message.model";
import { Request, Response } from "express";

export class MessageController {

    private messageRepository: MessageRepository;

    constructor() {
        this.messageRepository = new MessageRepository();
    }

    async create(req: Request, res: Response) {
        console.log("create cheguei aqui");
        const message = new Message(req.body)
        const newMessage = await this.messageRepository.save(message);
        return res.status(201).json(newMessage);
    }

    async getAll(req: Request, res: Response) {
        console.log("get all cheguei aqui");
        const messages = await this.messageRepository.getAll();
        return res.status(201).json(messages);
    }
}