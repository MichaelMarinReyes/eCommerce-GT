import { User } from "./user.model"; 

export interface Rating {
  idRating: number;
  user: User;
  stars: number;
  comment: string;
  createdAt: string;
}
