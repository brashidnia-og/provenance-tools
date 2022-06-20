import {Buffer} from 'buffer';

export default class Auth {
    static getAuthorizationHeader(user: string, password: string) {
        console.log( "Basic " + Buffer.from(user + ":" + password).toString("base64"));
    return "Basic " + Buffer.from(user + ":" + password).toString("base64");
    }
};