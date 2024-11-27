package lv.kristianskaneps.autoserviss.repository;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lv.kristianskaneps.autoserviss.model.ContactUs;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@ApplicationScoped
public class ContactUsRepository extends BaseRepository<ContactUs> {
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<ContactUs> getClientsThatWantToContactUs() {
        return find("replied = false", Sort.ascending("createdAt", "name")).stream().toList();
    }

//    @Transactional(Transactional.TxType.REQUIRED)
//    public @NotNull Uni<RowSet<Row>> add(@NotNull ContactUs entity) {
//        return database.client().preparedQuery(
//            """
//            insert into contact_us_requests(name, email, phone, subject, content, created_at) values (?1, ?2, ?3, ?4, ?5, ?6)
//            """
//        ).execute(
//                tuple(
//                        entity.name,
//                        entity.email,
//                        entity.phone,
//                        entity.subject,
//                        entity.content,
//                        LocalDateTime.ofInstant(entity.createdAt, ZoneOffset.UTC)
//                )
//        );
//    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(@NotNull ContactUs entity) {
        entity.delete();
    }
}
