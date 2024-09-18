export class Question {
  public id?;
  public content;
  public typeId;
  public levelId;
  public category;
  public subCategory;
  public mark;
  public expectedTime;
  public correctAnswersIds;
  public createdBy;
  public createdAt;
  public answers;

  constructor(
    content,
    typeId,
    levelId,
    category,
    subCategory,
    mark,
    expectedTime,
    correctAnswersIds,
    createdBy,
    createdAt,
    answers
  ) {
    this.content = content;
    this.typeId = typeId;
    this.levelId = levelId;
    this.category = category;
    this.subCategory = subCategory;
    this.mark = mark;
    this.expectedTime = expectedTime;
    this.correctAnswersIds = correctAnswersIds;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.answers = answers;
  }
}
