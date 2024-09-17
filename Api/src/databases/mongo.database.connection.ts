import { IDatabaseConnection } from "./database.connection.interface";
import mongoose from "mongoose";

export class MongoConnection implements IDatabaseConnection {
    
    async connect() {
        const uri = "mongodb+srv://adryan:adryan@cluster.obrpkn7.mongodb.net/?retryWrites=true&w=majority&appName=cluster";

        try {
            await mongoose.connect(uri);
        } catch(error: any) {
            console.error(error);
        }
    }

}