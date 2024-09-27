import "dotenv/config"
import {z} from "zod";


const envSchema = z.object({
    PORT:z.coerce.number().default(3001),
    MONGO_URI:z.string(),
});

export const env = envSchema.parse(process.env);

