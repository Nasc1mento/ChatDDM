import { env } from "../env";
import { IDatabaseConnection } from "./database.connection.interface";
import mongoose from "mongoose";

export class MongoConnection implements IDatabaseConnection {
    
    async connect() {
        const uri = env.MONGO_URI;

        try {
            await mongoose.connect(uri);
        } catch(error: any) {
            console.error(error);
        }
    }

}