package ph.utils;

/**��֤��ʵ��˼·��
*��Servlet�����������֤���ַ����У�������session�У�
*JSP����ͼƬ����ʽ������ʾ�����û���JSP����������֤�벢�ύʱ��
*����Ӧ��Servlet����֤�Ƿ���session�б������֤��һ�¡�
*/
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//����CheckCode��URL��ַΪ /CheckCode
@WebServlet("/CheckCode") 
public class CheckCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int width = 80; // ��֤��ͼƬ�Ŀ�
	private int height = 20; // ��֤��ͼƬ�ĸ�
	private int codeCount = 4; // ��֤��ͼƬ���ַ���
	private int x = 16;
	private int fontHeight = 16;
	private int codeY = 18;
	private final char[] codeSequence = 
		{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		// ����һ������ΪԤ����ͼ������֮һ��BufferedImage������ͼ��Ŀ��ߣ������ͣ�TYPE_INT_RGB��
		BufferedImage Img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// ����Graphics2D��Graphics2D����չ��Graphics �࣬���ṩ�Լ�����״������ת������ɫ������ı����ָ�Ϊ���ӵĿ���
		Graphics g = Img.getGraphics();

		Random random = new Random();
		// ��ͼ�����Ϊ��ɫ
		g.setColor(Color.WHITE);
		// ���ָ���ľ��Ρ�x,y�����Ϊ0����Ϊwidth,��Ϊheight
		g.fillRect(0, 0, width, height);
		// �������壬����Ĵ�СӦ�ø���ͼƬ�ĸ߶�������
		Font font = new Font("Times new Roman", Font.PLAIN, fontHeight);

		g.setColor(Color.black);
		g.setFont(font);
		Color juneFont = new Color(153, 204, 102);
		// �������130�������ߣ����ױ���������̽��
		g.setColor(juneFont);
		for (int i = 0; i < 130; i++) {

			// ����α�����
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(16); // 80/5=16
			int yl = random.nextInt(16);
			// �ڴ�ͼ�������ĵ�����ϵ�У�ʹ�õ�ǰ��ɫ�ڵ� (x1, y1) �� (x2, y2) ֮�仭һ����
			g.drawLine(x, y, x + xl, y + yl);

		}
		// randomCode���ڱ��������������֤�룬�Ա��û���¼�������֤,�̰߳�ȫ�Ŀɱ��ַ�����
		StringBuffer randomCode = new StringBuffer();
		// �������codeCount���ֵ���֤��
		for (int i = 0; i < codeCount; i++) {
			// ���� char �������ַ�����ʾ��ʽ
			String strRand = String.valueOf(codeSequence[random.nextInt(36)]);

			// �������������ɫ����֤����Ƶ�ͼ����
			// ��������ָ����ɫ����ɫ����ɫֵ�Ĳ�͸���� sRGB ��ɫ����Щֵ���� (0 - 255) �ķ�Χ��
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

			// ʹ�ô�ͼ�������ĵĵ�ǰ�������ɫ������ָ�� string �������ı���������ַ��Ļ���λ�ڴ�ͼ������������ϵ�� (x, y)
			// λ�ô���
			g.drawString(strRand, (i + 1) * x - 4, codeY);
			randomCode.append(strRand);
		}
		
		HttpSession session = request.getSession(); // ����λ���ֵ���֤�뱣�浽Session��
		session.setAttribute("realcode", randomCode.toString());
		
		// ��ֹ���������
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Cache-Control", "no-cache");// HTTP 1.1
		response.setDateHeader("Expires", 0); // �ڴ���������˷�ֹ����
		response.setContentType("image/gif"); // �������������ͻ��˵���Ӧ����������

		// ��ͼ�������Servlet�������,ServletOutputStream�ṩ����ͻ��˷��Ͷ��������ݵ������
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(Img, "gif", sos); // ʹ��֧�ָ�����ʽ������ ImageWriter ��һ��ͼ��д��
										// OutputStream
		sos.flush(); // ˢ�´��������ǿ��д�����л��������ֽ�
		sos.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
}