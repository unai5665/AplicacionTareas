package org.iesharia.aplicaciontareas.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TareaDao_Impl implements TareaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Tarea> __insertionAdapterOfTarea;

  private final EntityInsertionAdapter<TipoTarea> __insertionAdapterOfTipoTarea;

  private final EntityDeletionOrUpdateAdapter<Tarea> __deletionAdapterOfTarea;

  private final EntityDeletionOrUpdateAdapter<Tarea> __updateAdapterOfTarea;

  public TareaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTarea = new EntityInsertionAdapter<Tarea>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `tareas` (`id`,`titulo`,`descripcion`,`id_tipostareas`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Tarea entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitulo());
        statement.bindString(3, entity.getDescripcion());
        statement.bindLong(4, entity.getId_tipostareas());
      }
    };
    this.__insertionAdapterOfTipoTarea = new EntityInsertionAdapter<TipoTarea>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `tipostareas` (`id`,`titulo`) VALUES (nullif(?, 0),?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TipoTarea entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitulo());
      }
    };
    this.__deletionAdapterOfTarea = new EntityDeletionOrUpdateAdapter<Tarea>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tareas` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Tarea entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTarea = new EntityDeletionOrUpdateAdapter<Tarea>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tareas` SET `id` = ?,`titulo` = ?,`descripcion` = ?,`id_tipostareas` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Tarea entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitulo());
        statement.bindString(3, entity.getDescripcion());
        statement.bindLong(4, entity.getId_tipostareas());
        statement.bindLong(5, entity.getId());
      }
    };
  }

  @Override
  public Object insertarTarea(final Tarea tarea, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTarea.insertAndReturnId(tarea);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertarTipoTarea(final TipoTarea tipoTarea,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTipoTarea.insert(tipoTarea);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTarea(final Tarea tarea, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTarea.handle(tarea);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTarea(final Tarea tarea, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTarea.handle(tarea);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTareasConTipos(
      final Continuation<? super List<TareaDao.TareaConTipo>> $completion) {
    final String _sql = "\n"
            + "        SElECT Tareas.*, TiposTareas.titulo AS tipo\n"
            + "        FROM tareas\n"
            + "        INNER JOIN tipostareas ON Tareas.id_tipostareas = tipostareas.id\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TareaDao.TareaConTipo>>() {
      @Override
      @NonNull
      public List<TareaDao.TareaConTipo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfTipo = CursorUtil.getColumnIndexOrThrow(_cursor, "tipo");
          final List<TareaDao.TareaConTipo> _result = new ArrayList<TareaDao.TareaConTipo>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TareaDao.TareaConTipo _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpTitulo;
            _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            final String _tmpDescripcion;
            _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            final String _tmpTipo;
            _tmpTipo = _cursor.getString(_cursorIndexOfTipo);
            _item = new TareaDao.TareaConTipo(_tmpId,_tmpTitulo,_tmpDescripcion,_tmpTipo);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
