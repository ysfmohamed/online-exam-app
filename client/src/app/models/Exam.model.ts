export class Exam {
  private name: string;
  private createdBy: string;
  private passingScore: number;
  private questions: string[];

  constructor(
    name: string,
    createdBy: string,
    passingScore: number,
    questions: string[]
  ) {
    this.name = name;
    this.createdBy = createdBy;
    this.passingScore = passingScore;
    this.questions = questions;
  }
}
