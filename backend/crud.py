from sqlalchemy.orm import Session
from backend.models import Todo

def get_todos(db: Session, limit: int = 100):
    return db.query(Todo).limit(limit).all()

def get_todo(db: Session, todo_id: int):
    return db.query(Todo).filter(Todo.id == todo_id).first()
