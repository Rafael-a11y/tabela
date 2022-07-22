package tabelaDeFrutas.modelo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

public class DAO <E>
{
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Class<E> classe; //Recebe literalmente uma classe de parâmetro
	
	/* Serve para iniciar emf apenas uma única vez, já que este servirá para várias conexões
	em. Também serve para criar um bloco, pois objetos e métodos são exeutados apenas em
	blocos de código.*/ 
	
	static 
	{
		try
		{
			emf = Persistence.createEntityManagerFactory("Tabela-de-Frutas");
		}
		catch(Exception e)
		{
			System.out.println("EntityManagerFactory não foi gerado, emf nulo.");
			e.getMessage();
		}
	}
	
	public DAO() {this(null);}
	
	/*Passando uma classe E como parâmetro que sera usada em obterPorId() que usa Class.getName() para achar o registro equivalente no banco de dados. Acontece que o método
	find() que retorna o registro pesquisado, precisa do Class.name, cujo valor é guardado dentro do atributo classe*/
	public DAO(Class<E> classe)
	{
		this.classe = classe;
		em = emf.createEntityManager();
	}
	
	public DAO<E> comecarTransacao()
	{
		em.getTransaction().begin();
		return this;
	}
	
	public DAO<E> commitarTransacao()
	{
		em.getTransaction().commit();
		this.avisoVisual("Transação commitada com sucesso no banco de dados.");
		return this;
	}
	//Precisa estar dentro do contexto de transação para funcionar
	public DAO<E> persistirEntidade(E entidade) 
	{
		if(entidade != null)
		{
			em.persist(entidade);
		}
		return this;
	}
	
	//Precisa estar dentro do contexto de transação para funcionar
		public DAO<E> removerEntidade(E entidade)
		{
			if(entidade != null)
			{
				em.remove(entidade);
			}
			return this;
		}
		//Precisa estar dentro do contexto de transação para funcionar
		public DAO<E> alterarEntidade(E entidade)
		{
			/*É bom chamar o detach() para o caso da variável estiver nula e o JPA tentar comitá-la ao chamar commitarTransacao() por conta do estado gerenciado, evitando um
			 * IllegalArgumentException por tentar atualizar no banco de dados a partir de uma instância nula.*/
			em.detach(entidade);
			if(entidade != null)
			{
				em.merge(entidade);
			}
			return this;
		}
	
	public DAO<E> persistirDeFormaAtomica(E entidade)
	{
		this.comecarTransacao().persistirEntidade(entidade).commitarTransacao();
		return this;
	}
		
	/*Utiliza uma String que representa uma consulta JPQL, e uma classe de parâmetro. O Objeto TypedQuery<E> query recebe de valor a consulta propriamente dita. Para a consulta
	 * ser passada como o valor do objeto TypedQuery<E> query, chamamos o método createQuery(String argumento1, Class<E> argumrno2), o Class<E> passado precisa ser o mesmo
	 * passado no generics do TypedQuery, e a consulta também precisa coerente, não adianta você passar E como classe e tentar consultar F na consulta. Um exemplo de cnsulta
	 * seria: select e from E e; No lugar de 'e' você pode por o nome que mais achar coerente como alias, o 'E' representa a classe mapeada que você deseja buscar no banco
	 * de dados, esta classe precisa ser a mesma do TypedQuery<E>. O método setMaxResults(int argumento1) serve para limitar a quantidade de elementos retornados, o método
	 * getResultList serve para de fato retornar a lista de elementos obtidos com a consulta, este método está dentro da classe TypedQuery<E> e é chamado a partir de sua
	 * instância.  O método setFirstResult(int argumento1) serve para pular os 'argumento01' primeiros elementos da tabela no banco de dados.*/
	public List<E> obterTodos()
	{
		if(classe == null)
		{
			throw new UnsupportedOperationException("Classe nula."); 
		}
		String jpql = "select e from " + classe.getName() + " e";
		TypedQuery<E> query = em.createQuery(jpql, classe);
		return query.getResultList();
	}
	
	public void fecharEntityManager()
	{
		em.close();
	}
	
	/*Aqui está um motivo de usar Generics, com este método que usa um id, me permite usar este método para obter qualquer objeto de qualquer tipo, e por isso o motivo de 
	* fornecer uma classe no construtor, para poder referenciar o tipo genérico E como parâmetro e assim pesquisar o objeto desejado a partir do método Class.getName() 
	* que retorna a classe passada no construtor
	* Obtem um objeto do banco de dados pelo id, o método public E find(Class<E> argumento1, Object argumento2) faz uma consulta do tipo where, por isso é recomendável usar
	* um id como parâmetro, mas o método aceita qualquer Object como segundo parâmetro. O Class<E> deve estar mapeado no persistence.xml e é usado para se saber qual o 
	* tipo de objeto que será recuperado no banco de dados*/
	public E obterPorId(Object id)
	{
		return em.find(classe, id);
	}
	
	public void avisoVisual(String mensagem)
	{
		JOptionPane.showMessageDialog(null, mensagem );
	}
	
}
