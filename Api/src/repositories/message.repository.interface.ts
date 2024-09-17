export interface IMessageRepository<T> {
    save(t: T): Promise<T>;
    getAll(t: T): Promise<T[]>
}