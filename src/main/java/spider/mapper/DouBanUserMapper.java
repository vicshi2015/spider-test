package spider.mapper;

import spider.model.DouBanUser;

/**
 *
 * @author shiwei
 * @version $$Id: DouBanUserMapper, V 0.1 2017/1/18 11:32 shiwei Exp $$
 */
public interface DouBanUserMapper {
    public int insertAndGetId(DouBanUser user);

    public DouBanUser getUserByUserName(String userName);
}
