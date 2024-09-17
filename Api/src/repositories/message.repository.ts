import { IMessage, Message } from "../model/message.model";
import { IMessageRepository } from "./message.repository.interface";


export class MessageRepository implements IMessageRepository<IMessage> {
 
    private model: typeof Message;

    constructor() {
        this.model = Message;
    }

    async save(message: IMessage) : Promise<IMessage>{
        return this.model.create(message)
    }

    async getAll() : Promise<IMessage[]> {
        return this.model.find();
    }
}